/*
 *
 *  * cdoj, UESTC ACMICPC Online Judge
 *  * Copyright (c) 2013 fish <@link lyhypacm@gmail.com>,
 *  * 	mzry1992 <@link muziriyun@gmail.com>
 *  *
 *  * This program is free software; you can redistribute it and/or
 *  * modify it under the terms of the GNU General Public License
 *  * as published by the Free Software Foundation; either version 2
 *  * of the License, or (at your option) any later version.
 *  *
 *  * This program is distributed in the hope that it will be useful,
 *  * but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  * GNU General Public License for more details.
 *  *
 *  * You should have received a copy of the GNU General Public License
 *  * along with this program; if not, write to the Free Software
 *  * Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02110-1301, USA.
 *
 */

/**
 * Created with IntelliJ IDEA.
 * User: mzry1992
 * Date: 13-1-30
 * Time: 下午9:13
 * To change this template use File | Settings | File Templates.
 */

/**
 * current search condition
 */
var currentCondition;

/**
 * current problem list
 */
var problemList;

var visibleClass = 'icon-eye-open';
var unVisibleClass = 'icon-eye-close';

function editVisible(id,value) {
    var queryString = '/admin/problem/operator?method=edit';
    queryString += '&id='+id;
    queryString += '&field=isVisible';
    queryString += '&value='+value;
    $.post(queryString,function(data){
        if (data.result == "ok") {
            var icon = $('#visibleState[problemId="'+id+'"]');
            if (value == false)
            {
                icon.removeClass(visibleClass);
                icon.addClass(unVisibleClass);
            }
            else
            {
                icon.removeClass(unVisibleClass);
                icon.addClass(visibleClass);
            }
            icon.click(function(){editVisible(id,!value)});
        }
    });
}

function getTitle(problemId,title, source, isSpj, isVisible) {
    var html = '';

    html += '<i id="visibleState" problemId="'+problemId+'" class="';
    if (isVisible == true)
        html += visibleClass;
    else
        html += unVisibleClass;
    html += ' pull-left tags" onclick="editVisible('+problemId+','+(!isVisible)+');"/>';

    if (isSpj == true)
        html += '<span class="label label-important tags pull-left">SPJ</span>';

    html += '<a href="#'+problemId+'" title="'+source+'">'
        + title + '</a></span>';
    return html;
}

function getTags(tags) {
    var html = '';
    $.each(tags, function (index, value) {
        html += '<span class="label label-info pull-right tags">' + value + '</span>';
    });
    return html;
}

function getDifficulty(difficulty) {
    var html = '';
    for (var i = 2; i <= difficulty; i += 2)
        html += '<i class="icon-star"></i>';
    if (difficulty % 2 == 1)
        html += '<i class="icon-star-empty"></i>';
    return html;
}

function refreshProblemList(condition) {
    $.post('/admin/problem/search', condition, function (data) {
        console.log(condition);
        if (data.result == "error") {
            alert(data.error_msg);
            return;
        }

        //pagination
        $('#pageInfo').empty();
        $('#pageInfo').append(data.pageInfo);
        $('#pageInfo').find('a').click(function (e) {
            currentCondition.currentPage = $(this).attr("href");
            refreshProblemList(currentCondition);
            return false;
        });

        problemList = data.problemList;
        var tbody = $('#problemList');
        // remove old user list
        tbody.find('tr').remove();
        // put user list
        $.each(problemList, function (index, value) {
            var html = '<tr>' +
                '<td>' + value.problemId + '</td>' +
                '<td>' + getTitle(value.problemId,value.title, value.source, value.isSpj, value.isVisible) + getTags(value.tags) + '</td>' +
                '<td>' + getDifficulty(value.difficulty) + '</td>' +
                '<td><a href="/admin/problem/editor/' + value.problemId + '" title="Edit problem"><i class="icon-pencil"</a></td>' +
                '<td><a href="/admin/problem/data/' + value.problemId + '" title="Edit data"><i class="icon-cog"</a></td>' +
                '</tr>';
            tbody.append(html);
        });
    });
}

$(document).ready(function () {
    currentCondition = {
        "currentPage": null,
        "problemCondition.startId": undefined,
        "problemCondition.endId": undefined,
        "problemCondition.title": undefined,
        "problemCondition.source": undefined,
        "problemCondition.isVisible": undefined,
        "problemCondition.isSpj": undefined,
        "problemCondition.startDifficulty": undefined,
        "problemCondition.endDifficulty": undefined,
        "problemCondition.keyword": undefined
    }

    $('input#search').click(function (e) {
        $.each(currentCondition, function (index, value) {
            if (index.indexOf('.') != -1)
                currentCondition[index] = $('#' + index.replace('.', '_')).val();
        });
        currentCondition["problemCondition.isSpj"] = $(':radio[name="problemCondition.isSpj"]:checked').val();
        currentCondition["problemCondition.isVisible"] = $(':radio[name="problemCondition.isVisible"]:checked').val();
        if (currentCondition["problemCondition.isSpj"] == "all")
            currentCondition["problemCondition.isSpj"] = undefined;
        if (currentCondition["problemCondition.isVisible"] == "all")
            currentCondition["problemCondition.isVisible"] = undefined;
        currentCondition.currentPage = 1;
        refreshProblemList(currentCondition);
        $('#TabMenu a:first').tab('show');
        return false;
    });

    $('input#reset').click(function (e) {
        $.each(currentCondition, function (index, value) {
            if (index.indexOf('.') != -1)
                $('#' + index.replace('.', '_')).attr('value', '');
        });
        $(':radio[name="problemCondition.isSpj"]:nth(0)').attr("checked", true);
        $(':radio[name="problemCondition.isVisible"]:nth(0)').attr("checked", true);
        return false;
    });

    refreshProblemList(currentCondition);
});