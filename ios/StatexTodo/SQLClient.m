//
//  SQLClient.m
//  StatexTodo
//
//  Created by James Deng on 19/03/2016.
//  Copyright © 2016 Rewen. All rights reserved.
//

#import "SQLClient.h"
#import "sqlite3.h"

#include <regex.h>

static void sqlite_regexp(sqlite3_context* context, int argc, sqlite3_value** values) {
  if ( argc < 2 ) {
    sqlite3_result_error(context, "SQL function regexp() called with missing arguments.", -1);
    return;
  }
  
  char* reg = (char*)sqlite3_value_text(values[0]);
  char* text = (char*)sqlite3_value_text(values[1]);
  
  if ( argc != 2 || reg == 0 || text == 0) {
    sqlite3_result_error(context, "SQL function regexp() called with invalid arguments.", -1);
    return;
  }
  
  int ret;
  regex_t regex;
  
  ret = regcomp(&regex, reg, REG_EXTENDED | REG_NOSUB);
  if ( ret != 0 ) {
    sqlite3_result_error(context, "error compiling regular expression", -1);
    return;
  }
  
  ret = regexec(&regex, text , 0, NULL, 0);
  regfree(&regex);
  
  sqlite3_result_int(context, (ret != REG_NOMATCH));
}

@implementation SQLClient

+ (SQLiteResult*) open:(NSString*)path {
  NSString *docs = [NSSearchPathForDirectoriesInDomains(NSDocumentDirectory, NSUserDomainMask, YES) objectAtIndex: 0];
  NSString *dbPath = [docs stringByAppendingPathComponent: path];
  
  const char *name = [dbPath UTF8String];
  sqlite3 *db;
  SQLiteResult* pluginResult = nil;
  NSValue *dbPointer = nil;
  
  NSLog(@"open full db path: %@", dbPath);
  
  if (sqlite3_open(name, &db) != SQLITE_OK) {
    pluginResult = [SQLiteResult resultWithStatus:SQLiteStatus_ERROR messageAsString:@"Unable to open DB"];
    return pluginResult;
  } else {
    sqlite3_create_function(db, "regexp", 2, SQLITE_ANY, NULL, &sqlite_regexp, NULL, NULL);
    
    if(sqlite3_exec(db, (const char*)"SELECT count(*) FROM sqlite_master;", NULL, NULL, NULL) == SQLITE_OK) {
      dbPointer = [NSValue valueWithPointer:db];
      pluginResult = [SQLiteResult resultWithStatus:SQLiteStatus_OK messageAsArray:@[dbPointer]];
    } else {
      pluginResult = [SQLiteResult resultWithStatus:SQLiteStatus_ERROR messageAsString:@"Unable to open DB with key"];
    }
  }
  
  if (sqlite3_threadsafe()) {
    NSLog(@"Good news: SQLite is thread safe!");
  }
  else {
    NSLog(@"Warning: SQLite is not thread safe.");
  }
  
  return pluginResult;
}

@end