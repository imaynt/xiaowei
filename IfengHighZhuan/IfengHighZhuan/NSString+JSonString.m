//
//  NSString(JSonString).m
//  IfengHighZhuan
//
//  Created by 李胜男 on 15/10/13.
//  Copyright © 2015年 Linda Lee. All rights reserved.
//

#import "NSString+JSonString.h"

@implementation NSString (JSonString)

+ (NSDictionary *)dictionaryWithJsonString:(NSString *)jsonString {
    if (jsonString == nil) {
        return nil;
    }
    
    NSData *jsonData = [jsonString dataUsingEncoding:NSUTF8StringEncoding];
    NSError *err;
    NSDictionary *dic = [NSJSONSerialization JSONObjectWithData:jsonData
                                                        options:NSJSONReadingMutableContainers
                                                          error:&err];
    if(err) {
        NSLog(@"json解析失败：%@",err);
        return nil;
    }
    return dic;
}
@end
