//
//  SQLClient.h
//  StatexTodo
//
//  Created by James Deng on 19/03/2016.
//  Copyright © 2016 Rewen. All rights reserved.
//
#import <UIKit/UIKit.h>
#import "SQLiteResult.h"

@interface SQLClient : NSObject {}

+ (SQLiteResult*) open:(NSString*)path;

@end
